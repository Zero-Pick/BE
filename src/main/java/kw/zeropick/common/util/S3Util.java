package kw.zeropick.common.util;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Util {

	private final AmazonS3 s3Client;

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	@PostConstruct
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
			.build();
	}

	public String upload(MultipartFile file) {
		String imageUrl = "";
		String fileName = createFileName(file.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try (InputStream inputStream = file.getInputStream()) {
			s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			imageUrl = s3Client.getUrl(bucket, fileName).toString();
		} catch (IOException e) {
			throw new IllegalArgumentException("IMAGE_UPLOAD_ERROR");
		}
		return imageUrl;
	}

	// 이미지파일명 중복 방지
	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	// 파일 유효성 검사
	private String getFileExtension(String fileName) {
		if (fileName.length() == 0) {
			throw new IllegalArgumentException("IMAGE_UPLOAD_ERROR");
		}
		ArrayList<String> fileValidate = new ArrayList<>();
		fileValidate.add(".jpg");
		fileValidate.add(".jpeg");
		fileValidate.add(".png");
		fileValidate.add(".JPG");
		fileValidate.add(".JPEG");
		fileValidate.add(".PNG");
		fileValidate.add(".mp4");
		String idxFileName = fileName.substring(fileName.lastIndexOf("."));
		if (!fileValidate.contains(idxFileName)) {
			throw new IllegalArgumentException("IMAGE_UPLOAD_ERROR");
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}

	// DeleteObject를 통해 S3 파일 삭제
	public void deleteFile(String fileName) {
		String objectKey = parseObjectKeyFromUrl(fileName);
		// 삭제
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, objectKey);
		s3Client.deleteObject(deleteObjectRequest);
	}

	private String parseObjectKeyFromUrl(String objectUrl) {
		return objectUrl.substring(objectUrl.lastIndexOf('/') + 1);
	}

}
