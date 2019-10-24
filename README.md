# NasaSpaceAppChallenge - TeamUp
Application built to enhance the creation of Teams for the Nasa Space App Challenge

##Configuration
Inside the configuration file you can find a dummy mysql database attached 
and the ssl configuration

config file location:
`src/main/resources/application.properties`

to generate the certificate use:
`keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650`

if you instead already have the certificate use
`openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root`

##Run

to run the application just generate the .war package with 
`mvn install`

and run the package generated inside target with
`java -jar packagename.war`

default port `8443`

No auto http redirect enabled, the site page will be available as default in
`https://yourUrl:8443`