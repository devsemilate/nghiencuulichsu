package s3.lamphan.nghiencuulichsu.domain.repository.gateways;

/**
 * Created by lam.phan on 11/1/2016.
 */
public interface IGateWay {
    String getEndpoint();
    String getApplicationId();
    String getSecretKey();
}
