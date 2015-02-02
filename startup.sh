if [ -z ${JAVA_HOME} ]
then
	echo "JAVA_HOME not set yet,can't run java program!"
	exit -1
fi
CLASSPATH=.:${JAVA_HOME}/lib/tools.jar:${JAVA_HOME}/lib/dt.jar
JLIBDIR=./lib
export JLIBDIR
for LL in `ls ${JLIBDIR}/*.jar`
do
	CLASSPATH=${CLASSPATH}:${LL}
done
echo ${CLASSPATH}

export CLASSPATH

JAVA_OPTION="-Dfile.encoding=GBK -Xms256m -Xmx256m -XX:MaxPermSize=64m"
RUN_CLASS=com.lxq.batch.Main
${JAVA_HOME}/bin/java ${JAVA_OPTION} -classpath ${CLASSPATH} ${RUN_CLASS}

