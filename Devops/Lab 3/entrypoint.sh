#!/bin/sh

python manage.py makemigrations
python manage.py migrate
python manage.py collectstatic --no-input --clear
python manage.py createsuperuser

gunicorn locallibrary.wsgi:application --bind 0.0.0.0:8000
