package section23JavaNetworking;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class MainByteBuffer {

    public static void main(String[] args) {

        Consumer<ByteBuffer> printBuffer = (buffer) -> {
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.printf("\"%s\" ", new String(data, StandardCharsets.UTF_8));
        };

//        var printBuffer2 = new Consumer<ByteBuffer>() {
//            @Override
//            public void accept(ByteBuffer buffer) {
//                byte[] data = new byte[buffer.limit()];
//                buffer.get(data);
//                System.out.printf("\"%s\" ", new String(data, StandardCharsets.UTF_8));
//            }
//        };

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        doOperation("Write: ", buffer, b -> b.put("This is a test".getBytes()));

        doOperation("Flip (from Write to Read): ", buffer, ByteBuffer::flip);
        doOperation("Read and Print Value: ", buffer, printBuffer);

        // v1, write additional text
//        doOperation("Flip (from Read to Write): ", buffer, ByteBuffer::flip);
//        doOperation("1. Move position to end of text", buffer, (b) -> b.position(b.limit()));
//        doOperation("2. Change limit to capacity", buffer, (b) -> b.limit(b.capacity()));
//        doOperation("Append: ", buffer, b -> b.put(" This is a new test".getBytes()));

        // v2, write additional text
        doOperation("Flip (from Read to Write): ", buffer, ByteBuffer::flip);
        doOperation("Compact: ", buffer, ByteBuffer::compact);
        doOperation("Append: ", buffer, b -> b.put(" This is a new test".getBytes()));

        // v1, read data
//        doOperation("Flip (from Write to Read): ", buffer, ByteBuffer::flip);
//        doOperation("Read and Print Value: ", buffer, printBuffer);

        // v2, read data
        doOperation("Read and Print Value | slice: ", buffer.slice(0, buffer.position()), printBuffer);

        // write data
        doOperation("Append: ", buffer, b -> b.put(" *****".getBytes()));
        doOperation("Read and Print Value: ",
                buffer.slice(0, buffer.position()), printBuffer);
    }

    private static void doOperation(String op, ByteBuffer buffer, Consumer<ByteBuffer> c) {
        System.out.printf("%-35s", op);

        c.accept(buffer);

        System.out.printf("Capacity = %d, Limit = %d, Position = %d, Remaining = %d%n",
                buffer.capacity(),
                buffer.limit(),
                buffer.position(),
                buffer.remaining());
    }
}
