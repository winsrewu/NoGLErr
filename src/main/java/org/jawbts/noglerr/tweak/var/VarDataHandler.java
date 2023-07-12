package org.jawbts.noglerr.tweak.var;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class VarDataHandler extends DataHandlerBase {
    private final SavedData data = new SavedData();
    private List<Arg> argList = new ArrayList<>();
    private String argListFailReason = null;

    private Entity vEntity;
    private ClientWorld vWorld;
    private ClientPlayerEntity vPlayer;

    public VarDataHandler() {
        data.name = "unnamed";
        data.value = "0";
    }

    public VarDataHandler(String name, String value) {
        data.name = Utils.escapeString(name);
        data.value = Utils.escapeString(value);
    }

    public String getName() {
        return data.name;
    }

    public void setName(String name) {
        data.name = Utils.escapeString(name);
    }

    public String getData() {
        return data.value;
    }

    public void setData(String data) {
        this.data.value = Utils.escapeString(data);
    }

    public SavedData getSavedData() {
        return data;
    }

    public void setArgList(List<Arg> argList) {
        this.argList = new ArrayList<>(argList);
    }

    public void createArgList() {
        argList.clear();
        
        // 读取内容, 拆分成列表
        List<Arg> argCache = new ArrayList<>();
        try {
            StringReader reader = new StringReader(data.value);
            int key = 0, state = 0;
            char c = ' ';
            StringBuilder cache = new StringBuilder();
            while (key != -1) {
                if (state != 10) {
                    key = reader.read();
                    c = (char) key;
                } else {
                    state = 0;
                }

                if ((c == ' ' && state != 1) || c == '\\') {
                    continue;
                }
                if (state == 0) {
                    if (c == '"') {
                        state = 1;
                    } else if (c == '$') {
                        state = 2;
                    } else if (c == 't') {
                        argCache.add(new Arg(ArgType.BOOLEAN, "true"));
                    } else if (c == 'f') {
                        argCache.add(new Arg(ArgType.BOOLEAN, "false"));
                    } else if (('0' <= c && c <= '9') || c == '.') {
                        cache.append(c);
                        state = 3;
                    } else if (c == '+' || c == '-' || c == '/' || c == '*' || c == '%' || c == '=') {
                        String s = "";
                        s += c;
                        argCache.add(new Arg(ArgType.OPERATOR, s));
                    } else if (c == '(') {
                        argCache.add(new Arg(ArgType.PAREL, ""));
                    } else if (c == ')') {
                        argCache.add(new Arg(ArgType.PARER, ""));
                    } else if (c == '[') {
                        state = 4;
                    }
                } else if (state == 1) {
                    if (c == '"') {
                        state = 0;
                        argCache.add(new Arg(ArgType.STRING, cache.toString()));
                        cache = new StringBuilder();
                    } else {
                        cache.append(c);
                    }
                } else if (state == 2) {
                    if (c == '$') {
                        state = 0;
                        int counter = 0;
                        switch (cache.toString()) {
                            case "this.id", "this.age" ->
                                    argCache.add(new Arg(ArgType.INT, "", true, cache.toString()));
                            case "world.time" -> argCache.add(new Arg(ArgType.LONG, "", true, cache.toString()));
                            default -> counter++;
                        }
                        if (cache.toString().startsWith("this.data.")) {
                            argCache.add(new Arg(ArgType.INT, "", true, cache.toString()));
                        } else {
                            if (counter == 1) {
                                argCache.add(new Arg(ArgType.INT, "", true, ""));
                            }
                        }
                        cache = new StringBuilder();
                    } else {
                        cache.append(c);
                    }
                } else if (state == 3) {
                    if (('0' <= c && c <= '9') || c == '.') {
                        cache.append(c);
                    } else {
                        state = 0;
                        if (c == 'F') {
                            argCache.add(new Arg(ArgType.FLOAT, cache.toString()));
                        } else if (c == 'D') {
                            argCache.add(new Arg(ArgType.DOUBLE, cache.toString()));
                        } else if (c == 'L') {
                            argCache.add(new Arg(ArgType.LONG, cache.toString()));
                        } else {
                            argCache.add(new Arg(ArgType.INT, cache.toString()));
                            state = 10;
                        }
                        cache = new StringBuilder();
                    }
                } else if (state == 4) {
                    if (c == ']') {
                        state = 0;
                        argCache.add(new Arg(ArgType.OPERATOR, cache.toString()));
                        cache = new StringBuilder();
                    } else {
                        cache.append(c);
                    }
                }
            }
        } catch (Exception e) {
            argListFailReason = StringUtils.translate("noglerr.command.argListGenerateError");
            return;
        }

        // 将列表转为后缀表达
        Stack<Arg> argStack = new Stack<>();

        for (Arg arg : argCache) {
            if (arg.getType() == ArgType.OPERATOR) {
                if (argStack.isEmpty() || arg.getP() < argStack.peek().getP()) {
                    argStack.push(arg);
                } else {
                    while ((!argStack.isEmpty()) && argStack.peek().getP() < arg.getP()) {
                        argList.add(argStack.pop());
                    }
                    argStack.push(arg);
                }
            } else if (arg.getType() == ArgType.PAREL) {
                argStack.push(arg);
            } else if (arg.getType() == ArgType.PARER) {
                while ((!argStack.isEmpty()) && argStack.peek().getType() != ArgType.PAREL) {
                    argList.add(argStack.pop());
                }
                argStack.pop();
            } else {
                argList.add(arg);
            }
        }
        while (!argStack.isEmpty()) {
            argList.add(argStack.pop());
        }
    }

    public String getTreatedData(Entity entity, ClientWorld world, ClientPlayerEntity player) {
        vEntity = entity;
        vWorld = world;
        vPlayer = player;

        argListFailReason = null;
        createArgList();

        if (argListFailReason != null) {
            return argListFailReason;
        }

        Stack<Arg> argCache = new Stack<>();

        try {
            for (Arg arg : argList) {
                if (arg.getType() == ArgType.OPERATOR) {
                    Arg a, b, c;
                    switch (arg.getValue()) {
                        case "+", "-", "*", "/", "%", "=" -> {
                            b = argCache.pop();
                            a = argCache.pop();
                            c = null;
                        }
                        default -> {
                            a = argCache.pop();
                            b = null;
                            c = null;
                        }
                    }
                    switch (arg.getValue()) {
                        case "+" -> c = a.plus(b);
                        case "-" -> c = a.minus(b);
                        case "*" -> c = a.multiply(b);
                        case "/" -> c = a.divide(b);
                        case "%" -> c = a.mod(b);
                        case "=" -> {
                            assert b != null;
                            c = new Arg(ArgType.BOOLEAN, Boolean.toString(a.getTreatedValue().equalsIgnoreCase(b.getTreatedValue())));
                        }
                        case "int" -> c = a.tryConvertToInt();
                        case "long" -> c = a.tryConvertToLong();
                        case "float" -> c = a.tryConvertToFloat();
                        case "double" -> c = a.tryConvertToDouble();
                    }
                    argCache.push(c);
                } else {
                    argCache.push(arg);
                }
            }
        } catch (Arg.CalculationException e) {
            return e.getMessage();
        } catch (Exception e) {
            return StringUtils.translate("noglerr.command.argListCalculateError");
        }
        String ans;
        try {
            ans = argCache.peek().getTreatedValue();
        } catch (Exception e) {
            ans = e.toString();
        }
        if (Configs.Toggles.DEBUG.getBooleanValue()) {
            return argList.toString();
        }
        return ans;
    }

    public String toString() {
        return String.format("Name: %s Value: %s", data.name, data.value);
    }

    private enum ArgType {
        INT, STRING, LONG, FLOAT, DOUBLE, BOOLEAN, OPERATOR, PAREL, PARER;

        public static int getInt(String s) {
            return Integer.parseInt(s);
        }

        public static Long getLong(String s) {
            return Long.parseLong(s);
        }

        public static Float getFloat(String s) {
            return Float.parseFloat(s);
        }

        public static Double getDouble(String s) {
            return Double.parseDouble(s);
        }

        public static Boolean getBoolean(String s) {
            return Boolean.parseBoolean(s);
        }
    }

    private class Arg {
        private ArgType type;
        private final String value;
        private final boolean isVar;
        private final String varName;

        public Arg(ArgType type, String value) {
            this.type = type;
            this.value = value;
            isVar = false;
            varName = null;
        }

        public Arg(ArgType type, String value, boolean isVar, String varName) {
            this.type = type;
            this.value = value;
            this.isVar = isVar;
            this.varName = varName;
        }

        public ArgType getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }

        public boolean needUpdate() {
            return isVar;
        }

        public String toString() {
            return isVar ? type.name() + " " + varName : type.name() + " " + value;
        }

        public String getTreatedValue() {
            if (isVar) {
                if (varName.equals("")) {
                    return "NULL";
                }
                String ans = "NULL";
                switch (varName) {
                    case "this.id" -> ans = Integer.toString(vEntity.getId());
                    case "this.age" -> ans = Integer.toString(vEntity.age);
                    case "world.time" -> ans = Long.toString(vWorld.getTime());
                }
                if (varName.startsWith("this.data.")) {
                    String s = varName.substring(10);
                    NbtCompound nbt = new NbtCompound();
                    vEntity.writeNbt(nbt);
                    if (nbt.get(s) == null) {
                        return "NULL";
                    } else {
                        ans = Utils.escapeString(nbt.get(s).asString());
                        if (ans.length() < 3) {
                            return ans;
                        }
                        String substring = ans.substring(0, ans.length() - 2);
                        if (ans.endsWith("f")) {
                            type = ArgType.FLOAT;
                            ans = substring;
                        } else if (ans.endsWith("l")) {
                            type = ArgType.LONG;
                            ans = substring;
                        } else if (ans.endsWith("d")) {
                            type = ArgType.DOUBLE;
                            ans = substring;
                        } else if (ans.endsWith("s")) {
                            type = ArgType.INT;
                            ans = substring;
                        }
                    }
                }
                return ans;
            }
            return value;
        }

        public int getP() {
            if (this.type == ArgType.OPERATOR) {
                if (this.value.equals("+") || this.value.equals("-")) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (this.type == ArgType.PAREL || this.type == ArgType.PARER) {
                return 3;
            }
            return 0;
        }

        public class CalculationException extends Exception{
            CalculationException(Arg a, Arg b, String reason) {
                super(a + " can't " + reason + " " + b);
            }
            CalculationException(String reason) {
                super(reason);
            }
        }

        public Arg plus(Arg a) throws CalculationException {
            if (getType() != a.getType()) {
                throw new CalculationException(this, a, "plus");
            }
            if (type == ArgType.INT) {
                return new Arg(ArgType.INT, Integer.toString(((ArgType.getInt(getTreatedValue()) + ArgType.getInt(a.getTreatedValue())))));
            }
            if (type == ArgType.FLOAT) {
                return new Arg(ArgType.FLOAT, Float.toString((ArgType.getFloat(getTreatedValue()) + ArgType.getFloat(a.getTreatedValue()))));
            }
            if (type == ArgType.LONG) {
                return new Arg(ArgType.LONG, Long.toString((ArgType.getLong(getTreatedValue()) + ArgType.getLong(a.getTreatedValue()))));
            }
            if (type == ArgType.DOUBLE) {
                return new Arg(ArgType.DOUBLE, Double.toString((ArgType.getDouble(getTreatedValue()) + ArgType.getDouble(a.getTreatedValue()))));
            }
            if (type == ArgType.STRING) {
                return new Arg(ArgType.STRING, toString() + a);
            }
            throw new CalculationException(this, a, "plus");
        }

        public Arg minus(Arg a) throws CalculationException {
            if (getType() != a.getType()) {
                throw new CalculationException(this, a, "minus");
            }
            if (type == ArgType.INT) {
                return new Arg(ArgType.INT, Integer.toString(((ArgType.getInt(getTreatedValue()) - ArgType.getInt(a.getTreatedValue())))));
            }
            if (type == ArgType.FLOAT) {
                return new Arg(ArgType.FLOAT, Float.toString((ArgType.getFloat(getTreatedValue()) - ArgType.getFloat(a.getTreatedValue()))));
            }
            if (type == ArgType.LONG) {
                return new Arg(ArgType.LONG, Long.toString((ArgType.getLong(getTreatedValue()) - ArgType.getLong(a.getTreatedValue()))));
            }
            if (type == ArgType.DOUBLE) {
                return new Arg(ArgType.DOUBLE, Double.toString((ArgType.getDouble(getTreatedValue()) - ArgType.getDouble(a.getTreatedValue()))));
            }
            throw new CalculationException(this, a, "minus");
        }

        public Arg multiply(Arg a) throws CalculationException {
            if (getType() != a.getType()) {
                throw new CalculationException(this, a, "multiply");
            }
            if (type == ArgType.INT) {
                return new Arg(ArgType.INT, Integer.toString(((ArgType.getInt(getTreatedValue()) * ArgType.getInt(a.getTreatedValue())))));
            }
            if (type == ArgType.FLOAT) {
                return new Arg(ArgType.FLOAT, Float.toString((ArgType.getFloat(getTreatedValue()) * ArgType.getFloat(a.getTreatedValue()))));
            }
            if (type == ArgType.LONG) {
                return new Arg(ArgType.LONG, Long.toString((ArgType.getLong(getTreatedValue()) * ArgType.getLong(a.getTreatedValue()))));
            }
            if (type == ArgType.DOUBLE) {
                return new Arg(ArgType.DOUBLE, Double.toString((ArgType.getDouble(getTreatedValue()) * ArgType.getDouble(a.getTreatedValue()))));
            }
            throw new CalculationException(this, a, "multiply");
        }

        public Arg divide(Arg a) throws CalculationException {
            if (getType() != a.getType()) {
                throw new CalculationException(this, a, "divide");
            }
            try {
                if (type == ArgType.INT) {
                    return new Arg(ArgType.INT, Integer.toString(((ArgType.getInt(getTreatedValue()) / ArgType.getInt(a.getTreatedValue())))));
                }
                if (type == ArgType.FLOAT) {
                    return new Arg(ArgType.FLOAT, Float.toString((ArgType.getFloat(getTreatedValue()) / ArgType.getFloat(a.getTreatedValue()))));
                }
                if (type == ArgType.LONG) {
                    return new Arg(ArgType.LONG, Long.toString((ArgType.getLong(getTreatedValue()) / ArgType.getLong(a.getTreatedValue()))));
                }
                if (type == ArgType.DOUBLE) {
                    return new Arg(ArgType.DOUBLE, Double.toString((ArgType.getDouble(getTreatedValue()) / ArgType.getDouble(a.getTreatedValue()))));
                }
            } catch (ArithmeticException e) {
                throw new CalculationException("can't divide by 0");
            }
            throw new CalculationException(this, a, "divide");
        }

        public Arg mod(Arg a) throws CalculationException {
            if (getType() != a.getType()) {
                throw new CalculationException(this, a, "mod");
            }
            try {
                if (type == ArgType.INT) {
                    return new Arg(ArgType.INT, Integer.toString(((ArgType.getInt(getTreatedValue()) % ArgType.getInt(a.getTreatedValue())))));
                }
                if (type == ArgType.FLOAT) {
                    return new Arg(ArgType.FLOAT, Float.toString((ArgType.getFloat(getTreatedValue()) % ArgType.getFloat(a.getTreatedValue()))));
                }
                if (type == ArgType.LONG) {
                    return new Arg(ArgType.LONG, Long.toString((ArgType.getLong(getTreatedValue()) % ArgType.getLong(a.getTreatedValue()))));
                }
                if (type == ArgType.DOUBLE) {
                    return new Arg(ArgType.DOUBLE, Double.toString((ArgType.getDouble(getTreatedValue()) % ArgType.getDouble(a.getTreatedValue()))));
                }
            } catch (ArithmeticException e) {
                throw new CalculationException("can't mod by 0");
            }
            throw new CalculationException(this, a, "mod");
        }

        Arg tryConvertToInt() throws CalculationException {
            boolean canConvert;
            switch (type) {
                case INT, LONG, FLOAT, DOUBLE -> canConvert = true;
                default -> canConvert = false;
            }
            if (canConvert) {
                return new Arg(ArgType.INT, Integer.toString((int)Double.parseDouble(getTreatedValue())));
            }
            throw new CalculationException("can't convert");
        }

        Arg tryConvertToLong() throws CalculationException {
            boolean canConvert;
            switch (type) {
                case INT, LONG, FLOAT, DOUBLE -> canConvert = true;
                default -> canConvert = false;
            }
            if (canConvert) {
                return new Arg(ArgType.LONG, Long.toString((long)Double.parseDouble(getTreatedValue())));
            }
            throw new CalculationException("can't convert");
        }

        Arg tryConvertToDouble() throws CalculationException {
            boolean canConvert;
            switch (type) {
                case INT, LONG, FLOAT, DOUBLE -> canConvert = true;
                default -> canConvert = false;
            }
            if (canConvert) {
                return new Arg(ArgType.DOUBLE, Double.toString(Double.parseDouble(getTreatedValue())));
            }
            throw new CalculationException("can't convert");
        }

        Arg tryConvertToFloat() throws CalculationException {
            boolean canConvert;
            switch (type) {
                case INT, LONG, FLOAT, DOUBLE -> canConvert = true;
                default -> canConvert = false;
            }
            if (canConvert) {
                return new Arg(ArgType.FLOAT, Float.toString((float)Double.parseDouble(getTreatedValue())));
            }
            throw new CalculationException("can't convert");
        }
    }
}