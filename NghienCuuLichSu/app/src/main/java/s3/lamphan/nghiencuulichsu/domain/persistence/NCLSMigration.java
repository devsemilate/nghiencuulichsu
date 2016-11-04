package s3.lamphan.nghiencuulichsu.domain.persistence;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by lam.phan on 10/31/2016.
 */
public class NCLSMigration implements RealmMigration{
    public static final int VERSION = 0;

    public NCLSMigration() {
    }

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    }

    @Override
    public int hashCode() {
        return 88;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof NCLSMigration);
    }
}
