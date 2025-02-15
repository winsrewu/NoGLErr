**Warning:**  
This Minecraft mod is provided as-is, and its use carries certain risks. Please read the following carefully before proceeding:

1. **Server Ban Risk:**  
   Using this mod on a server may result in a ban, depending on the server's rules and policies. Ensure you have explicit permission from the server administrator before installing or using this mod.

2. **Potential Harm to Your Computer:**  
   Incorrect usage or installation of this mod may cause irreversible damage to your computer, including but not limited to data loss, system instability, or hardware issues. Use it at your own risk.

3. **No Liability:**  
   The developer of this mod is not responsible for any consequences arising from its use, including bans, data loss, or damage to your computer or server. By using this mod, you acknowledge that you understand the risks and accept full responsibility for your actions.

**Proceed with caution.** Make sure you know what you are doing and that your server administrator is aware of and approves your use of this mod. If you are unsure, do not use it.

**警告：**  
本 Minecraft 模组按“原样”提供，使用它可能带来一定的风险。请在继续之前仔细阅读以下内容：

1. **服务器封禁风险：**  
   在服务器上使用此模组可能导致封禁，具体取决于服务器的规则和政策。在使用或安装此模组之前，请确保已获得服务器管理员的明确许可。

2. **对计算机的潜在危害：**  
   不正确使用或安装此模组可能会对您的计算机造成不可逆的损害，包括但不限于数据丢失、系统不稳定或硬件问题。使用此模组需自行承担风险。

3. **免责声明：**  
   本模组的开发者不对因使用此模组而产生的任何后果负责，包括但不限于封禁、数据丢失或对计算机或服务器的损害。使用此模组即表示您已了解相关风险，并愿意为自己的行为承担全部责任。

**请谨慎操作。** 确保您清楚自己在做什么，并确保服务器管理员知晓并同意您使用此模组。如果您不确定，请不要使用。

# NoGLErr
很杂的辅助模组,
用于一些奇奇怪怪的特定用途

A interesting mod,
for something special and strange.

不要看这个名字，这只是它一个废掉的功能，而且是第一个  
所以它变成了项目名

Don't mind the name, it's just one of its deprecated features, and it's the first  
So it becomes the name of the project

Since 1.0.8, you need to download the mod from modrinth.  
自1.0.8开始, 你需要去modrinth下载这个模组.  
https://modrinth.com/mod/noglerr

# 版权
此模组自 1.0.5 开始, 1.0.9 结束, 集成了 vosk 的库, 感谢他们  
https://github.com/alphacep/vosk-api/tree/master/java/demo  
此模组自 1.0.7 开始集成了 openjdk 的 nashorn , 感谢他们  
https://github.com/openjdk/nashorn

# 版本
客户端需装, 服务端无效  
需要前置模组 malilib  

Only Client needed  
Requires malilib

# 注意
遇到乱码的, 请在mc启动参数将编码调成UTF-8!  

# 功能
检测malilib配置, 里面功能默认是关闭的  
快捷键是n + g + c
### 屏蔽OpenGL Error
已废弃
### “showvar”命令
注意: text的value必须为Text对象, 和原版指令格式相同  
target名称就是你想展示的text的名称  
target的value是实体选择器  
如果要调用var, 在text里面写$(var name)$  
var里面可以使用$(name)$ 来调用关于实体的信息  
例如this.id即为实体id, this.age为实体存在时间, world.time为世界时间  
如果以this.data.(name)开头, 后面的部分和/data get @(这个实体) (name)一样  
例如$this.data.Inventory$ 返回实体物品栏 
对于var内部的逻辑运算, 很不稳定, 但是能用  
在写入数字的时候, 需要在其后面加入其类型, 例如123L, 12.8F, int不需要加, 当然你写123I也可以  
不同类型计算会报错, 例如$world.time$ + 1  
world.time是long型, 必须$world.time$ + 1L  
强制类型转换是[], 例如$world.time$ + ([long]1)  
等于符号是单个=  
由于符号奇怪的优先级, 推荐多加括号  
例如 1 = 1 = true 它会先判断 1 = true 然后结果再去 = 1   

还可以使用 /showvar script folder 打开脚本文件夹(只支持windows)  
里面创建一个(多个也行)以.js结尾的文件, 里面使用javascript注册一个函数, 比如叫test  
那么可以使用$test$调用该函数  
同时, 你也可以使用/showvar script call (函数名称]) (参数列表)来调用  
记得更改后使用 /showvar script reload 刷新  
函数默认传入两个参数, 第一个是entity, 调用该函数的实体, 如果用指令调用, 则是玩家,
第二个是utils, 就是工具集. 请你自己去github翻翻看里面有什么. 
在 org.jawbts.noglerr.tweak.var.javascript 里面.    


支持的变量列表:  
this.data.* 类型不定 功能相当于/data get  
this.id Int 实体id  
this.age Int 实体存活时间  
world.time Long 世界时间, tick为单位

### js脚本
建议你去翻一翻这个仓库的代码
(主要内容在 org.jawbts.noglerr.tweak.var.javascript 包下, 
还有一点在 org.jawbts.noglerr.tweak.var.ScriptVarManager 类里面),
同时学习一下nashorn是怎么用的, 
你就会发现这玩意还挺好用的 (至少对我来说)

### 语音转文字
首先在 .minecraft/voskModels 下载模型 https://alphacephei.com/vosk/models  
记得解压 完成应该有这样一个文件 voskModels\xxx\README  
使用 /vosk reload 重载模型  
其他的看配置界面  
1.0.9更新之后, 这个就是一坨屎, 建议你不要用.
当然如果你擅长捣鼓, 那么你就会发现这个其实是能用的.
为什么我会这么做呢? 因为我的电脑的算力和内存不支持我.