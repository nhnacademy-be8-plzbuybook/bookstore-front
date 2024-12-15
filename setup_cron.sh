#!/bin/bash
# renew_cert.sh 경로 설정
RENEW_CERT_PATH="./renew_cert.sh"
# 기존 크론 작업 백업
crontab -l > mycron
# renew_cert.sh 작업 추가
echo "0 2 * * * $RENEW_CERT_PATH" >> mycron
# 새로운 크론 작업 설치
crontab mycron
# 임시 파일 제거
rm mycron