FROM henriquej0904/java:16-jdk-headless-ubuntu-20.04

LABEL build="docker build -t icl:tools ."

# Update & Install
RUN apt update && \
apt -y full-upgrade && \
apt -y install javacc wget unzip nano && \
rm -r /var/lib/apt/lists/*

# Download jasmin and install
RUN wget -O /opt/jasmin.zip "https://sourceforge.net/projects/jasmin/files/latest/download" && \
unzip -d /opt /opt/jasmin.zip && rm /opt/jasmin.zip

# Set jasmin Env var and create jasmin alias
RUN echo >> /root/.bashrc && \
echo export JASMIN_JAR_PATH=$(find /opt jasmin.jar | grep -e "jasmin.jar") >> /root/.bashrc && \
echo alias jasmin=\"java -jar $(find /opt jasmin.jar | grep -e "jasmin.jar")\" >> /root/.bashrc

CMD ["/bin/bash"]