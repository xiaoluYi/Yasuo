package com.sjl.yuehu.api;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.sjl.yuehu.Constants;
import com.sjl.yuehu.util.ParamsUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by 小鹿 on 2017/2/8.
 */
public class DefaultConfig {

    String BASE_ENDPOINT = "https://json.azi.bz";

    public static class CustomGsonConverFactory extends Converter.Factory {

        /**
         * Create an instance using {@code gson} for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        public static CustomGsonConverFactory create(Gson gson) {
            return new CustomGsonConverFactory(gson);
        }

        private final Gson gson;

        private CustomGsonConverFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new CustomGsonResponseBodyConverter<>(adapter, gson);
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new CustomGsonRequestBodyConverter<>(gson, adapter);
        }
    }

    static final class CustomGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CustomGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    static final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;
        private final Gson gson;

        CustomGsonResponseBodyConverter(TypeAdapter<T> adapter, Gson gson) {
            this.adapter = adapter;
            this.gson = gson;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            LogUtil.e(value+"");
            String aesValue = value.string();
            JsonObject jsonValue;
            try {
                String jsonResult = ParamsUtil.miwJiema(aesValue);
                jsonValue = gson.fromJson(jsonResult, JsonObject.class);
//                LogUtil.e(jsonValue+"");
            } catch (Exception e) {
                throw new JsonIOException(e);
            }
            int code = jsonValue != null ? jsonValue.get(Constants.REMOTE_CODE).getAsInt() : -1;
            if (code == Constants.CODE_SUCCESS) {
                String json = jsonValue.toString();
                return adapter.fromJson(json);
            } else {
                String errMsg = jsonValue != null ? jsonValue.get(Constants.REMOTE_MESSAGE).getAsString() : null;
                throw new ResultException(code, errMsg);
            }
        }
    }

    final class FileRequestBody extends RequestBody {

        private File file;
        private ProgressListener mProgressListener;

        private boolean finished;

        public FileRequestBody(File file, ProgressListener progressListener) {
            this.file = file;
            this.mProgressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse("image/*");
        }

        @Override
        public long contentLength() throws IOException {
            return file.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            BufferedSink bufferedSink = Okio.buffer(sink(sink));
            Source source = Okio.source(file);

            bufferedSink.writeAll(source);

            bufferedSink.flush();
            Util.closeQuietly(source);
        }

        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                long bytesWritten = 0L;
                long contentLength = 0L;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0)
                        contentLength = contentLength();
                    bytesWritten += byteCount;

                    if (mProgressListener != null) {
                        if (!finished) {
                            mProgressListener.onProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                            finished = bytesWritten == contentLength;
                        }
                    }
                }
            };
        }
    }

    interface ProgressListener {
        void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
    }
}
