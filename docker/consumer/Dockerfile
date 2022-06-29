FROM python:2.7-onbuild
ENV PYTHONPATH /usr/src/app
WORKDIR /app
ADD consumer.py /app
ADD run.sh /app
ADD requirements.txt /app
RUN pip install --requirement /app/requirements.txt
RUN chmod a+x run.sh
CMD ["./run.sh"]
