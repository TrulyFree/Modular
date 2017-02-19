#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
JAVADOCS_PROMPT="Build javadocs? [Y/n] "
MVN_OPTIONS_PROMPT="Maven arguments? "
MSG="$1"
BRANCH="$2"
PUSH="$3"

cd "$TARGET_DIR"

echo -n "$JAVADOCS_PROMPT"
read javadocs

echo -n "$MVN_OPTIONS_PROMPT"
read mvn_opt

mvn clean

if [[ $javadocs =~ ^[Yy]$ ]]
then
	mvn javadoc:javadoc
	mvn javadoc:test-javadoc
fi

mvn $mvn_opt
mvn surefire-report:report-only
mvn site -DgenerateReports=false

git show-ref --verify --quiet refs/heads/"$BRANCH"
if [[ $? -ne 0 ]]; then
	git checkout -b "$BRANCH"
else
	git checkout "$BRANCH"
fi

git add -A
git commit -m "$MSG"

if [[ $PUSH =~ ^[Yy]$ ]]
then
	git push origin "$BRANCH"
fi