# AjNetworkService
A tiny library to handle network calls without writing new network layer. 
Use this only in your POC's. 
Agenda is to avoid writing network layer for demo applications.

Steps to Integrate

1. Add below piece of code at the repositories list
        maven {
            url "https://ajduties.jfrog.io/artifactory/aj-network-service/"
            credentials {
                username = "anonymus"
                password = ""
            }
        }
2. add implementation 'com.ae.ajnetworkservice:AjNetworkService:1.0.0' to your respective modules build.gradle


Steps to Use
1. Initialize AjNetworkService class, which requires a base url as parameter
2. Use getCall method for any get call, which requires Url (remaining url from base url along with path params if any)
3. Use postCall method for ant post call, which requires Url (remaining url from base url along with path params if any)
