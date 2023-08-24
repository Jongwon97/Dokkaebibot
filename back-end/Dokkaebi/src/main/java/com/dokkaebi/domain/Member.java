package com.dokkaebi.domain;

import jakarta.persistence.Cache;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.Comment;
import com.dokkaebi.domain.community.Friend;
import com.dokkaebi.domain.community.Invite;
import com.dokkaebi.domain.community.Like;
import com.dokkaebi.domain.community.Message;
import com.dokkaebi.domain.community.Scrap;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

	// DB가 mysql 이므로 기본키 IDENTITY 전략 사용
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String email;
	private String password;
	private String nickname;
	private String refreshToken;
	private Integer iconNumber;
	private String introduction;

	@Column(columnDefinition = "boolean default false")
	private boolean isAdmin;

	@OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
	private List<Article> articleList = new ArrayList<>();

	@OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
	private List<Comment> commentList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Like> likeList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Scrap> scrapList = new ArrayList<>();

	@OneToMany(mappedBy = "member1", cascade = CascadeType.ALL)
	private List<Friend> memberList1 = new ArrayList<>();

	@OneToMany(mappedBy = "member2", cascade = CascadeType.ALL)
	private List<Friend> memberList2 = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Message> messageSenderList = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<Message> messagesReceiverList = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Invite> inviteSenderList = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<Invite> inviteReceiverList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<StudyData> studyDataList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	List<MemberInterest> memberInterests = new ArrayList<>();    // 멤버 관심사

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private MemberAnalysis memberAnalysis;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private Device device;

}
