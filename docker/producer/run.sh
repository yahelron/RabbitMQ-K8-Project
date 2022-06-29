#!/bin/bash
python producer.py -p 5672 -s rabbitmq -m "$(date) Hello" -r 30