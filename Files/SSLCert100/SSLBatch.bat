@echo off
set OPENSSL_CONF=openssl.cfg
openssl req -nodes -newkey rsa:%1 -keyout %2 -out %3 -subj %4
pause
