docker run -p 8180:8080 -p 8443:8443 -e KEYCLOAK_USER_FILE=/tmp/user.txt -e KEYCLOAK_PASSWORD_FILE=/tmp/password.txt -e KEYCLOAK_IMPORT=/tmp/sample-realm.json -v ${PWD}/files:/tmp -d jboss/keycloak
