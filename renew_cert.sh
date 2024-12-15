#!/bin/bash
# 인증서 갱신
sudo certbot renew
# Nginx 서버 재시작
sudo systemctl reload nginx