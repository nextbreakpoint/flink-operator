#!/usr/bin/env sh

set -x
set -e

export OPERATOR_HOST=$(minikube ip)

./gradlew integrationTest --info --tests="com.nextbreakpoint.flinkoperator.integration.cases.${1}Test"

