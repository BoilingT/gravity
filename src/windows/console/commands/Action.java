package windows.console.commands;

public abstract class Action implements IAction{
	private String info;
	
	public String getInfo() {
		return this.info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public Action(String info) {
		this.info = info;
	}
	
	public Action() {
		this.info = "";
	}
	
	@Override
	public abstract void run(String args);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
