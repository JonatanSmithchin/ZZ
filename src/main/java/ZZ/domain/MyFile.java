package ZZ.domain;

import java.io.Serializable;

public class MyFile implements Serializable {

        private byte[] fileBytes;
        private int fileLen ;
        private String dest ;
        private String src ;

    public MyFile(byte[] fileBytes, int fileLen, String dest, String src) {
        this.fileBytes = fileBytes;
        this.fileLen = fileLen;
        this.dest = dest;
        this.src = src;
    }

    public byte[] getFileBytes() {
            return fileBytes;
        }

        public void setFileBytes(byte[] fileBytes) {
            this.fileBytes = fileBytes;
        }

        public int getFileLen() {
            return fileLen;
        }

        public void setFileLen(int fileLen) {
            this.fileLen = fileLen;
        }

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

}
