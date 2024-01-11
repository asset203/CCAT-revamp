# Stage
FROM nginx:1.20.2-alpine

LABEL maintainer="ahmed.elshinawy@asset.com.eg"

COPY dist/default.conf /etc/nginx/conf.d/default.conf

COPY dist/ccat-app /usr/share/nginx/html/ccat