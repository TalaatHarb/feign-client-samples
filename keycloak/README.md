# Running a KEYCLOAK server using docker
Run the following command from inside this folder:

    docker run -p 8180:8080 -e KEYCLOAK_USER_FILE=user.txt -e KEYCLOAK_PASSWORD_FILE=password.txt \
    -e KEYCLOAK_IMPORT=sample-realm.json -v sample-realm.json:sample-realm.json jboss/keycloak
