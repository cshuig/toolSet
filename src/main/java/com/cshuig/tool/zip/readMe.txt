数据库表导出：

--mysql备份数据库的命令 
mysqldump -u -root -p ggzypz > c:/ggzypz.sql

--oracle备份数据库的命令
exp ggzypz/ggzypz@orcl file=c:/ggzypz.sql tables=(TB_YW_FABB DJ)


--查看cmd帮助命令
cmd /?


对于Linux而言，压缩使用的是 gzip，只能压缩单个文件，不能压缩文件夹，所以需要先对文件夹大包tar
例如：2013 --> 2013.tar(没有压缩，只是打包)  -->gzip -- 2013.tar.gz

写到硬盘：输出流
从硬盘读取文件到内存：输入流	如：FileInputStream fis = new FileInputStream(file);

TB_XT_DATABACK
ID
START_TIME
END_TIME
OPER_ID
OPER_NAME
OPER_TIME
VALIDFLAG
WJMC
WJBH
OPET_TYPE
