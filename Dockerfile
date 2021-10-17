FROM henriquej0904/java:16-jdk-ubuntu-20.04

LABEL build="docker build -t icl-tools ."

# Update & Install javacc
RUN apt update && \
apt -y full-upgrade && \
apt -y install javacc wget unzip

# Download jasmin and install
RUN wget -O /opt/jasmin.zip "https://sourceforge.net/projects/jasmin/files/latest/download" && \
unzip -d /opt /opt/jasmin.zip && rm /opt/jasmin.zip

# Create jasmin alias
RUN echo >> /root/.bashrc && \
echo alias jasmin=\"java -jar $(find /opt jasmin.jar | grep -e "jasmin.jar")\" >> /root/.bashrc

CMD ["/bin/bash"]