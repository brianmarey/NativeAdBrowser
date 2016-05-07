package com.careydevelopment.nativeadbrowser.jpa.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "domain_ad")
public class DomainAd {
	private static final long MILLIS_PER_DAY = 24 * 3600 * 1000;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="domain")
	private Domain domain;
	
	@ManyToOne
	@JoinColumn(name="native_ad")
	private NativeAd nativeAd;
	
	@Column(name="first_seen")
	private Date firstSeen;
	
	@Column(name="last_seen")
	private Date lastSeen;
	
	public DomainAd() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public NativeAd getNativeAd() {
		return nativeAd;
	}

	public void setNativeAd(NativeAd nativeAd) {
		this.nativeAd = nativeAd;
	}

	public Date getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(Date firstSeen) {
		this.firstSeen = firstSeen;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}
	
	public String getLastSeenStr() {
		DateFormat format = new SimpleDateFormat("MM/dd/yy");
		
		if (lastSeen != null) {
			return format.format(lastSeen);
		} else return "No Date Found!";
	}

	public Long getDaysRunning() {
		Long daysRunning = new Long(0);
		
		if (lastSeen != null && firstSeen != null) {
			long msDiff= lastSeen.getTime() - firstSeen.getTime();
			daysRunning = Math.round(msDiff / ((double)MILLIS_PER_DAY));
		}
		
		return daysRunning;
	}
}
