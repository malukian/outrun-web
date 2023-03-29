package edu.fra.uas.common;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
// is an abstract base class for all entities in the application
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> {

	@Id
	@GeneratedValue
	private PK id;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		if (!(obj instanceof BaseEntity<?>)) {
			return false;
		}

		BaseEntity<?> other = (BaseEntity<?>) obj;
		return id != null && id.equals(other.id);
	}

}
