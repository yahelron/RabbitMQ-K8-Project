FROM python
ENV PYTHONPATH /usr/src/app
WORKDIR /app
ADD consumer.py /app
ADD run.sh /app
ADD requirements.txt /app
RUN pip install --requirement /app/requirements.txt
EXPOSE 9422/tcp
RUN chmod a+x run.sh
CMD ["./run.sh"]
