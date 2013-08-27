package net.paperpixel.fk.kube;

public final class KubeFactory {
	
	public static final AbstractKube getKube(int row, int col){
		//TODO chose what type of Kube to return according to its line and col numbers
		if(col > -1) {
			return new NoteKube(row, col);
		}

		return null;
	}
}
