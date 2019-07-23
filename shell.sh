mvn clean install
~/software/apache-tomcat-8.5.39/bin/shutdown.sh
sleep 5
rm -rf ~/software/apache-tomcat-8.5.39/webapps/*
cp target/pravah-1.0.0.war ~/Software/apache-tomcat-8.5.39/webapps/pravah.war
~/software/apache-tomcat-8.5.39/bin/catalina.sh start
tail -100f ~/software/apache-tomcat-8.5.39/logs/catalina.out
