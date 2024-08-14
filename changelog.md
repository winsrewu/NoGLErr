# 1.0.9
## feat
- 分离了vosk, 现在使用socket来从vosk服务器获取
- 修复一些 mc 1.21 支持方面的问题

# 1.0.8
## feat
- 在script里面支持向全服聊天发送消息以及指令
- 支持直接使用指令调用js函数

## perf
- 支持了 mc 1.21

# 1.0.7
## feat
- 加入script var相关功能
- 加入更新检测
## fix
- 修复vosk关闭时开游戏会null pointer exception
- 修复vosk开游戏无论开不开都会启动一次
## perf
- 更新了两处style相关, 移除两处不必要的翻译文本
- 优化某些操作, 修复某些潜在错误
  1. ClientTickHandler.java 加入了可能会被引用的ClientPlayer对象
  2. 加入了大量的@NotNull以及@Nullable@Override注解
  3. 为某些重要方法编写了Java doc
  4. 修复PlayerMessageSender可能的多线程问题
  5. VarDataHandler.java中为避免不正确调用, 加了一堆throw new UnsupportedOperationException();
  6. 优化了ScriptVar的打开文件夹方式, 引用了Mc自己的代码, 现在可支持更多操作系统
  7. 在几个Manager里头, 查找返回值可能为null的情况处, 改用了Optional对象以增加可读性
- 在更新检测方面作出了修改
  1. 版本号从硬编码变为从fabric那块读取
  2. 把带url的Text对象生成搞到了utils里头
- 修复某些地方调用pms.add()时不必要的translate
