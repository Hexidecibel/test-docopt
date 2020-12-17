#!/usr/bin/env bash

mvn -q \
  exec:java \
  -Dexec.mainClass="net.hexi.App" \
  -Dexec.args="$(printf "'%s' " "${@:-}")"
