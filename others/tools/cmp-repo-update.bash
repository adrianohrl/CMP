#!/bin/bash

CMP_DIRECTORY=../..
if [ ${#} = 1 ]; then
	CMP_DIRECTORY=${1}
fi
GITHUB_USER="adrianohrl"
GITHUB_REPOSITORY="cmp"
GITHUB_BRANCH="cmp-repo"

./mvn-repo-update.bash -d ${CMP_DIRECTORY} -u ${GITHUB_USER} -r ${GITHUB_REPOSITORY} -b ${GITHUB_BRANCH}