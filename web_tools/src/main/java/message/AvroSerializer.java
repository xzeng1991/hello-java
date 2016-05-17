package message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public class AvroSerializer<T> {
	public byte[] serialize(T input, DatumWriter<T> writer) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		Encoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
		writer.write(input, encoder);
		encoder.flush();

		return stream.toByteArray();
	}

	public T deserialize(byte[] bytes, DatumReader<T> reader)
			throws IOException {
		Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
		return reader.read(null, decoder);
	}
}
