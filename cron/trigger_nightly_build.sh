#!/bin/bash

_project=$1
_branch=$2
_circle_token=$3
_environment=$4
_suite_name=$5

trigger_build_url=https://circleci.com/api/v1/project/PatrickPriestley/q4testsuite/tree/master?circle-token=5439aa08dd79b73f2f13535f252c0d69dbb31edb

post_data=$(cat <<EOF
{
  "build_parameters": {
    "TEST_ENV": "$_environment",
    "SUITE_NAME": "$_suite_name"
  }
}
EOF)

curl \
--header "Accept: application/json" \
--header "Content-Type: application/json" \
--data "${post_data}" \
--request POST ${trigger_build_url}