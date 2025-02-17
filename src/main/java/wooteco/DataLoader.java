package wooteco;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wooteco.auth.dao.MemberDao;
import wooteco.auth.domain.Member;
import wooteco.subway.dao.LineDao;
import wooteco.subway.dao.SectionDao;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.Section;
import wooteco.subway.domain.Station;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {
    private StationDao stationDao;
    private LineDao lineDao;
    private SectionDao sectionDao;
    private MemberDao memberDao;
    private PasswordEncoder passwordEncoder;

    public DataLoader(StationDao stationDao, LineDao lineDao, SectionDao sectionDao, MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Station 강남역 = stationDao.insert(new Station("강남역"));
        Station 판교역 = stationDao.insert(new Station("판교역"));
        Station 정자역 = stationDao.insert(new Station("정자역"));
        Station 역삼역 = stationDao.insert(new Station("역삼역"));
        Station 잠실역 = stationDao.insert(new Station("잠실역"));

        Line 신분당선 = lineDao.insert(new Line("신분당선", "red lighten-1"));
        신분당선.addSection(new Section(강남역, 판교역, 10));
        신분당선.addSection(new Section(판교역, 정자역, 10));
        sectionDao.insertSections(신분당선);

        Line 이호선 = lineDao.insert(new Line("2호선", "green lighten-1"));
        이호선.addSection(new Section(강남역, 역삼역, 10));
        이호선.addSection(new Section(역삼역, 잠실역, 10));
        sectionDao.insertSections(이호선);

        Member member = new Member("email@email.com", passwordEncoder.encode("password"), 10);
        Member memberA = new Member("a@a.a", passwordEncoder.encode("1"), 20);

        memberDao.insert(member);
        memberDao.insert(memberA);
    }
}

