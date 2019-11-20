package Entities;

import java.io.ByteArrayInputStream;

public class ProfessorDataPojo {
	
	private Professor professor;
	private ByteArrayInputStream FPDates;
	private Integer  fpSize;
	
	public ProfessorDataPojo()
	{
		
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
