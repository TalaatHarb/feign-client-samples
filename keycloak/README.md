# Running a KEYCLOAK server using docker
Run the following command from inside this folder:

    docker run -p 8180:8080 -e KEYCLOAK_USER_FILE=/tmp/user.txt -e KEYCLOAK_PASSWORD_FILE=/tmp/password.txt -e KEYCLOAK_IMPORT=/tmp/sample-realm.json -v ${PWD}/files:/tmp -d jboss/keycloak

It might take up to 4 minutes to start
you can access its admin console using the user name and password in the corresponding files

After that enter the admin console and copy the secret from the client: sample-client to your configuration for the micro-service
