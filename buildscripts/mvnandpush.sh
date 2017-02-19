#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MVN_OPTIONS_PROMPT="Maven arguments? "
MSG="$1"
BRANCH_PROMPT="Branch? "
PUSH_PROMPT="Push to remote? [Y/n] "

cd "$TARGET_DIR"

echo -n "$MVN_OPTIONS_PROMPT"
read mvn_opt

mvn clean
mvn $mvn_opt
mvn surefire-report:report-only
mvn site -DgenerateReports=false

echo -n "$BRANCH_PROMPT"
read branch

git show-ref --verify --quiet refs/heads/"$branch"
if [[ $? -ne 0 ]]; then
	git checkout -b "$branch"
else
	git checkout "$branch"
fi

git add -A
git commit -m "$MSG"

echo -n "$PUSH_PROMPT"
read reply
if [[ $reply =~ ^[Yy]$ ]]
then
	git push origin "$branch"
fi