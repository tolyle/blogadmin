# 更新日志

___

2023-05-7

* 使用高性能的`fastjson2`替换SpringBoot内置的`Jackson`

2023-05-9

* 增加多环境配置文件，在启动springboot的时候根据开发者IP来判断加载哪个配置文件
* 使用`org.hidetake.ssh`和`shadow`打包好的jar自动传输到服务器上
* 使用脚本进行`springboot`的重启