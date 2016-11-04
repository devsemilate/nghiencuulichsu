package s3.lamphan.nghiencuulichsu.domain.repository.gateways;

/**
 * Created by lam.phan on 11/1/2016.
 */
public class BaseGateWay implements IGateWay{
    private static final String BaseDomain = "https://api.backendless.com/";
    private static final String ApplicationId = "9D89A9B5-2150-E1ED-FFE7-C7AA8A3D8100";
    private static final String SecretKey = "E408A124-88C4-A7BF-FF2B-99BB55F92500";

    public BaseGateWay() {
    }

    @Override
    public String getEndpoint() {
        return BaseDomain;
    }

    @Override
    public String getApplicationId() {
        return ApplicationId;
    }

    @Override
    public String getSecretKey() {
        return SecretKey;
    }
}
