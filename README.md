## Chat2QQ+

> **说明**

Chat2QQ+ 是 [Chat2QQ](https://github.com/DreamVoid/Chat2QQ) 的分支, 用于添加我需要的功能.

如果你有好的想法请点击上方 `Issues` 按钮, 发送一个功能请求. 如果我能做到就会去实现出来. 

! 注意, 此版本不兼容 Bukkit 服务器, 最低需要使用 1.18.2 版本的 Spigot (Chat2QQ-1.7.4 及以下版本支持 MC1.13.2).



> **下载**

可以点击右侧 `Releases` 按钮下载jar文件. 对于新添加的功能请在这里提问(而不是 DreamVoid/Chat2QQ).

如果需要开发版(包含一些未发布的功能和BUG), 可以点击上方 `Actions`, 选择第一个, 找到最下面的jar文件. 



> **订阅更新**

如果想要订阅更新, 请点击右上角 `Watch` 按钮, 选择 `Custom` 中的 `Releases` !

---

**功能列表**

- 执行指令
```yaml
  # 在QQ群中运行指令 [需要单独添加 QQ群]
  # 此模块不处理黑名单 blacklist
  run-command:
    enabled: false
    # 启用的 QQ群
    qq-group:
      - 1000001

    # 指令前缀, 可以是多个字符, 比如 "~$"
    command-prefix: '/'
    # 指令最大长度 (不包括指令前缀)
    command-max-length: 255
    # 获取指令的正则表达式, 当第一个捕获组的内容与指令白名单中的匹配时则允许运行 (不带斜杠或前缀)
    regex-command-main: '^([^ ]+)'

    # 是否发送指令的输出, 关闭可提高性能或解决一些兼容性问题
    return: true
    # 等待指令运行多长时间再将结果发送到QQ群 (毫秒), 需要开启 run-command.return
    return-sleep: 300
    # 是否将指令的输出打印到控制台和日志
    return-log: true

    # 执行不在白名单中的指令时发送返回消息
    message-miss: '未命中的指令'
    # 运行无返回指令的消息
    message-no-out: '运行无返回指令'

    # 设置各组可执行的主命令白名单 (不带斜杠或前缀)
    # 权限更高的用户将可以使用更低的用户的指令
    # 如果添加一条 ___ALL_COMMAND___ 作为指令, 则表示此组可以使用所有指令, 此功能请勿随意使用 !
    group:
      # permission_<int> 是 MiraiMC 获取到的权限数字, 以后更新了其他权限只需要以此格式添加即可使用
      permission_2: # 群主
      #- chat2qq
      permission_1: # 管理员
      #- spark
      permission_0: # 成员
      #- list
      #- tps
```

- 匹配游戏名
```yaml
  # 从 群名片(%nick%) 中匹配 MC 可用的游戏名称
  # 添加变量: %regex_nick% - 使用正则匹配到的名称, 需要开启 cleanup-name 功能
  cleanup-name:
    enabled: false
    # 程序取第一个捕获组的结果
    regex: '([a-zA-Z0-9_]{3,16})'
    # 如果匹配不到, 则使用以下字符串
    # %nick% - 群名片
    # %qq% - qq号
    not-captured: '%nick%'
```

- 消息预处理
```yaml
  # 预处理 %message% 中的消息
  pretreatment:
    enabled: true
    # **使用方法**
    # list:
    #   - 匹配方式: prefix (前缀匹配), 可用处理方式: to_all
    #                                           to_replace
    #             contain (包含), 可用处理方式: to_all
    #                                        to_replace
    #             equal (完全相等), 可用处理方式: to_all
    #             regular (正则匹配), 可用处理方式: to_all
    #                                           to_regular
    #     处理方式: to_all (替换整条消息)
    #             to_replace (替换匹配到的部分)
    #             to_regular (使用正则替换, 可使用正则变量)
    #
    #     是否发送: send (填写 send 配置将取消转发送匹配到的消息, 不需要时请忽略)
    #
    # > 正则的性能较差, 请尽量避免使用很多正则
    # 示例配置, 默认配置了一些可能有用的功能:
    list:

      # 卡片消息, JSON
      - prefix: '{"app":"com.tencent.'
        to_all: '[卡片消息]'

      # 群公告, JSON
      - prefix: '{"app":"com.tencent.mannounce"'
        to_all: '[群公告]'

      # 视频, 字符串
      - prefix: '你的QQ暂不支持查看视频短片'
        to_all: '[视频]'

      # 文件, 字符串, 调整格式
      - prefix: '[文件]'
        to_replace: '[文件] '

      # 解决 Emoji 显示为未知字符
      - regular: '[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]'
        to_regular: '[Emoji]'
```

- 预设格式调整配置
```yaml
  # 预设的格式调整功能
  other-format-presets:
    # 是否删除 %message% 消息 中的格式化字符
    render-message_format-code: false
    # 删除 %message% 消息 前后的空格和空行
    message-trim: true
    # 是否删除 %nick% 群名片 中的格式化字符
    render-nick_format-code: true

    # 聊天消息过长时转换为悬浮文本
    long-message:
      enabled: true
      # 以下任意一个条件成立时被判定为长消息, 若需取消一个, 请改为很大的数
      # 条件1: 消息长度达到此值
      condition-length: 210
      # 条件2: 换行数量达到此值, 在 message-trim 之后运行
      condition-line_num: 6
      # 显示为
      message: '§f[§7长消息§f]'

    # 是否启用 "更好的多行消息"
    multiline-message:
      enabled: true
      line-0: '' # [多行消息]
      line-prefix: '  '

    # 是否将聊天消息转发到控制台/日志
    message-to-log: true
```

- 引用回复
```yaml
  # 引用回复
  # 添加变量: %_reply_%
  # 如果是回复消息, 则为变量赋值并为消息添加悬浮文本框用于显示内容. 可以将鼠标悬停在消息上查看回复的内容
  reply-message:
    # 可用变量:
    # %qq% - 被回复的消息的发送者QQ号
    # %c_name% - 群名片 - 需要开启 aplini.format-qq-id
    var: '§f[§7回复 @%c_name%§f] '

    # 可用变量:
    # %_/n_% - 换行
    # %qq% - 被回复的消息的发送者QQ号
    # %c_name% - 群名片 - 需要开启 aplini.format-qq-id
    # %message% - 回复内容
    # %main_message% - 当前消息的完整内容
    message: '§f[§7引用 @%c_name%§f] §7%message%§r%_/n_%%_/n_%§f%main_message%'

    # 删除重复@ :: 如果引用回复对象等于消息开头的@对象, 则删除消息开头的 @
    del-duplicates-at: true
```

- `qchat` 指令
```yaml
  # 发送消息的指令
  # /qchat <消息>  - 玩家使用此指令
  # /qchat [自定义名称] <消息>  - 非玩家实体或其他程序使用此指令
  qchat:
    # 使用上方 general.group-ids 中配置的群
    use-general-group-ids: true
    # 消息转发到哪些群, 需要 use-general-group-ids: false
    group-ids:
      - 1000000
    # 非玩家 执行指令时忽略 [自定义名称] 参数
    use-fill-name: false
    # 如果获取不到名称 (开启 auto-other-name 或参数不足) 则使用以下设定值
    fill-name: '控制台'
    # 转发到QQ群的格式
    # %name% - 玩家名称或自定义名称或 fill-other-name
    # %message% - 消息
    qq-format: '[%name%] %message%'
    # 是否同时将消息广播到MC服务器
    mc-broadcast: true
    # 广播到MC服务器的
    mc-format: '§f[§7%name%§f] §r%message%'
```

- [前置] 群成员信息缓存
```yaml
  # [前置] 群成员信息缓存, 测试功能
  player-cache:
    # 在指定机器人登录时运行此程序
    enabled: true
    # 自动更新缓存
    auto-update: true
    # 测试.自动更新时重建所有缓存数据
    #test-auto-update-all: false

    # 使用上方 general.group-ids 中配置的群
    use-general-group-ids: true
    # 缓存哪些群, 需要 use-general-group-ids: false
    group-ids:
      - 1000000
```

- 使用 @群名片
```yaml
  # 将 %message% 中的 @qqID 替换为 @名称
  # 需要开启 aplini.player-cache
  format-qq-id:
    enabled: true
    # 用于匹配 @qqID 的正则
    regular: '(@[0-9]{5,11})'
    # 格式
    # %qq% - qq号
    # %name% - 名称
    format: '[@%name%]'
    # 最多匹配几次, 防止刷屏浪费性能
    max-cycles-num: 7
```

**指令和权限**
```yaml
commands:
  qchat:
    description: 发送聊天消息到QQ群
    permission: chat2qq.command.qchat
  chat2qq:
    description: Chat2QQ 插件主命令
permissions:
  chat2qq.join.silent:
    description: 允许悄悄加入服务器
    default: false
  chat2qq.quit.silent:
    description: 允许悄悄离开服务器
    default: false
  chat2qq.command.qchat:
    description: 允许使用 /qchat
    default: op
  chat2qq.command.chat2qq:
    description: 允许使用 /chat2qq
    default: op
  chat2qq:.command.setgroupcacheall:
    description: 允许使用 /chat2qq setgroupcacheall
    default: op
```