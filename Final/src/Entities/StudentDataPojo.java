package Entities;

import java.io.ByteArrayInputStream;

public class StudentDataPojo {

	private Student student;
	private Branch dega;
	private Year viti;
	private Parallel paraleli;
	private ByteArrayInputStream FPDates;
	private Integer  fpSize;
	
	public StudentDataPojo()
	{
		
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Branch getDega() {
		return dega;
	}

	public void setDega(Branch dega) {
		this.dega = dega;
	}

	public Year getViti() {
		return viti;
	}

	public void setViti(Year viti) {
		this.viti = viti;
	}

	public Parallel getParaleli() {
		return paraleli;
	}

	public void setParaleli(Parallel paraleli) {
		this.paraleli = paraleli;
	}

	public ByteArrayInputStream getFPDates() {
		return FPDates;
	}

	public void setFPDates(ByteArrayInputStream fPDates) {
		FPDates = fPDates;
	}

	public Integer getFpSize() {
		return fpSize;
	}

	public void setFpSize(Integer fpSize) {
		this.fpSize = fpSize;
	}	
}
