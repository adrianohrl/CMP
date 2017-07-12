#!/bin/bash

PROJECT_DIR=.
REPO_USER="adrianohrl"
USAGE="Usage: mvn-repo-update [--branch | -b <branch>] 
                       [--directory | -d <directory>] 
                       [--help | -h] 
                       [--repository | -r <repository>] 
                       [--user | -u <user>]

  Author: Adriano Henrique Rossette Leite
  Version: 1.0.0
  Description: This script updates a Maven project in a GitHub
               repository given its user, name, and branch.

  Options:

    -b, --branch <branch>
         Informs the repository branch name.
    -d, --directory <directory>
         Informs the path of the pom.xml file of the desired 
         Maven project.
    -h, --help
         Shows this helper message for usage info.
    -r, --repository <repository>
         Informs the repository name on GitHub.
    -u, --user <name> 
         Informs the GitHub user's name.

         "    

if [ ${#} = 0 ]; then
	echo "${USAGE}"
	exit
fi

while [ ${#} -gt 0 ]; do
	case "${1}" in
	    -b|--branch)
		    REPO_BRANCH="${2}"
		    shift
		    ;;
	    -d|--directory)
		    PROJECT_DIR="${2}"
		    shift
		    ;;
	    -r|--repository)
		    REPO_NAME="${2}"
		    shift
		    ;;
	    -u|--user)
		    REPO_USER="${2}"
		    shift
		    ;;
	    -h|--help)
			echo "${USAGE}"
			exit
		    ;;
	    *)
		    ;;
	esac
	shift
done

if [ ! -f ${PROJECT_DIR}/pom.xml ]; then
    echo "ERROR: ${PROJECT_DIR}/pom.xml file not found!!!"
    echo "Aborting..."
    exit 
fi
PROJECT_POM_FILE=$(cat ${PROJECT_DIR}/pom.xml)
PROJECT_GROUP_ID=$(grep -oPm1 "(?<=<groupId>)[^<]+" <<< "$PROJECT_POM_FILE")
PROJECT_ARTIFACT_ID=$(grep -oPm1 "(?<=<artifactId>)[^<]+" <<< "$PROJECT_POM_FILE")
if [ -z "$PROJECT_ARTIFACT_ID" ]; then
	echo "ERROR: not found the Maven artifact id!!!"
	echo "Aborting..."
	exit 
fi
PROJECT_VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" <<< "$PROJECT_POM_FILE")
PROJECT_JAR_FILE=${PROJECT_DIR}/target/${PROJECT_ARTIFACT_ID}-${PROJECT_VERSION}.jar
if [ -z "${REPO_NAME+xxx}" ]; then
	REPO_NAME="${PROJECT_ARTIFACT_ID}"
fi
if [ -z "${REPO_BRANCH+xxx}" ]; then
	REPO_BRANCH="${PROJECT_ARTIFACT_ID}-repo"
fi
REPO_URL="https://github.com/${REPO_USER}/${REPO_NAME}"
TEMP_DIR=~/.mvn-repo-update
REPO_LOCAL_DIR=${TEMP_DIR}/${PROJECT_ARTIFACT_ID}

echo "PROJECT_DIR          =  ${PROJECT_DIR}"
echo "PROJECT_GROUP_ID     =  ${PROJECT_GROUP_ID}"
echo "PROJECT_ARTIFACT_ID  =  ${PROJECT_ARTIFACT_ID}"
echo "PROJECT_VERSION      =  ${PROJECT_VERSION}"
echo "PROJECT_JAR_FILE     =  ${PROJECT_JAR_FILE}"
echo "REPO_URL             =  ${REPO_URL}"
echo "REPO_BRANCH          =  ${REPO_BRANCH}"

mvn clean install --file ${PROJECT_DIR}/pom.xml
git clone ${REPO_URL} --branch ${REPO_BRANCH} --single-branch ${REPO_LOCAL_DIR}
mvn install:install-file -DgroupId=${PROJECT_GROUP_ID} -DartifactId=${PROJECT_ARTIFACT_ID} -Dversion=${PROJECT_VERSION} -Dfile=${PROJECT_JAR_FILE} -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPO_LOCAL_DIR} -DcreateChecksum=true
git -C ${REPO_LOCAL_DIR} add --all 
git -C ${REPO_LOCAL_DIR} commit -m "released version ${PROJECT_VERSION}"
git -C ${REPO_LOCAL_DIR} push origin ${REPO_BRANCH}
echo "Removing ${TEMP_DIR} directory..."
sudo rm -rf ${TEMP_DIR}
echo "Released version ${PROJECT_VERSION} of the ${PROJECT_GROUP_ID}.${PROJECT_ARTIFACT_ID} maven project at ${REPO_URL} repository on ${REPO_BRANCH} branch."
