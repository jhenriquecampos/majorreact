package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NetflixMyListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetflixMyList.class);
        NetflixMyList netflixMyList1 = new NetflixMyList();
        netflixMyList1.setId(1L);
        NetflixMyList netflixMyList2 = new NetflixMyList();
        netflixMyList2.setId(netflixMyList1.getId());
        assertThat(netflixMyList1).isEqualTo(netflixMyList2);
        netflixMyList2.setId(2L);
        assertThat(netflixMyList1).isNotEqualTo(netflixMyList2);
        netflixMyList1.setId(null);
        assertThat(netflixMyList1).isNotEqualTo(netflixMyList2);
    }
}
