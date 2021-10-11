FROM ubuntu

# Update & Install java16 & javacc
RUN apt update && \
apt -y full-upgrade && \
apt -y install openjdk-16-jdk javacc wget unzip

# Download jasmin and install
RUN wget -O /opt/jasmin.zip "https://sourceforge.net/projects/jasmin/files/latest/download" && \
unzip -d /opt /opt/jasmin.zip && rm /opt/jasmin.zip

# Create jasmin alias
RUN echo >> /root/.bashrc && \
echo alias jasmin=\"java -jar $(find /opt jasmin.jar | grep -e "jasmin.jar")\" >> /root/.bashrc

CMD ["/bin/bash"]