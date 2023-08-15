# NoGLErr
很杂的辅助模组,
用于一些奇奇怪怪的特定用途

A interesting mod,
for something special and strange.

不要看这个名字，这只是它一个废掉的功能，而且是第一个  
所以它变成了项目名

Don't look at the name, it's just one of its deprecated features and it's the first  
So it becomes the name of the project

# 版权
此模组自 1.0.5 开始集成了 vosk 的库, 感谢他们  
https://github.com/alphacep/vosk-api/tree/master/java/demo  

# 版本
原则上只支持1.17.X  
客户端需装, 服务端无效  
需要前置模组 malilib  

Mod for MC 1.17.X  
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


支持的变量列表:  
this.data.* 类型不定 功能相当于/data get  
this.id Int 实体id  
this.age Int 实体存活时间  
world.time Long 世界时间, tick为单位

### 语音转文字
首先在 .minecraft/voskModels 下载模型 https://alphacephei.com/vosk/models  
记得解压 完成应该有这样一个文件 voskModels\xxx\README  
使用 /vosk reload 重载模型  
其他的看配置界面  
