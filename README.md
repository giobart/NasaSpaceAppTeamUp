# NasaSpaceAppTeamUp
Application built for creation of Teams for Nasa Space App Challenge in Pisa

In configuration you can find a dummy mysql database attached 
and the ssl configuration

to generate the certificate use:
`keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650`

if you instead already have the certificate use
`openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root`