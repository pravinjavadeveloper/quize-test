package pravin.rv.quizzz.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "completed_test")
public class CompletedTest extends BaseModel {

	private String createdBy;
	private Integer userId;
	private Integer quizId;
	private Integer correctQuest;
	private Integer totalQuest;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

	private Date createdDate;

	private Boolean isDone = false;

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the quizId
	 */
	public Integer getQuizId() {
		return quizId;
	}

	/**
	 * @param quizId the quizId to set
	 */
	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}

	

	

	/**
	 * @param date the createdDate to set
	 */
	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}

	/**
	 * @return the isDone
	 */
	public Boolean getIsDone() {
		return isDone;
	}

	/**
	 * @param isDone the isDone to set
	 */
	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	/**
	 * @return the correctQuest
	 */
	public Integer getCorrectQuest() {
		return correctQuest;
	}

	/**
	 * @param correctQuest the correctQuest to set
	 */
	public void setCorrectQuest(Integer correctQuest) {
		this.correctQuest = correctQuest;
	}

	/**
	 * @return the totalQuest
	 */
	public Integer getTotalQuest() {
		return totalQuest;
	}

	/**
	 * @param totalQuest the totalQuest to set
	 */
	public void setTotalQuest(Integer totalQuest) {
		this.totalQuest = totalQuest;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "CompletedTest [correctQuest=" + correctQuest + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", id=" + id + ", isDone=" + isDone + ", quizId=" + quizId + ", totalQuest="
				+ totalQuest + ", userId=" + userId + "]";
	}

	//@Override
	// @JsonIgnore
	// public User getUser() {
	// 	return getCreatedBy();
	// }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	


	
}
