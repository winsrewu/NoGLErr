# NoGLErr
很杂的辅助模组,
用于一些奇奇怪怪的特定用途

A interesting mod,
for something special and strange.

不要看这个名字，这只是它一个废掉的功能，而且是第一个  
所以它变成了项目名

Don't look at the name, it's just one of its deprecated features and it's the first  
So it becomes the name of the project

# 版本
原则上只支持1.17.X  
客户端需装, 服务端无效  
需要前置模组 malilib  

Mod for MC 1.17.X  
Only Client needed  
Requires malilib

# 功能
检测malilib配置, 里面功能默认是关闭的  
快捷键是n + g + c
### 屏蔽OpenGL Error
已废弃
### “showvar”命令
注意: text的value必须为Text对象, 和原版指令格式相同  
target名称就是你想展示的text的名称  
如果要调用var, 在text里面写$(var name)$  
var里面可以使用$(name)$ 来调用关于实体的信息  
例如this.id即为实体id, this.age为实体存在时间, world.time为世界时间
如果以this.data.(name)开头, 后面的部分和/data get @(这个实体) (name)一样  
例如$this.data.Inventory$ 返回实体物品栏  