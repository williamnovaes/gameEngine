package game;

import java.awt.event.KeyEvent;

public enum EMove {
	UP(KeyEvent.VK_W) {
		@Override
		public void action(Entity entity) {
			entity.moveUp();
		}

		@Override
		public void actionReverse(Entity entity) {
			entity.moveStop();
		}
	},
	LEFT(KeyEvent.VK_A) {
		@Override
		public void action(Entity entity) {
			entity.moveLeft();
		}

		@Override
		public void actionReverse(Entity entity) {
			entity.moveStop();
		}
	},
	DOWN(KeyEvent.VK_S) {
		@Override
		public void action(Entity entity) {
			entity.moveDown();
		}

		@Override
		public void actionReverse(Entity entity) {
			entity.moveStop();
		}
	},
	RIGHT(KeyEvent.VK_D) {
		@Override
		public void action(Entity entity) {
			entity.moveRight();
		}

		@Override
		public void actionReverse(Entity entity) {
			entity.moveStop();
		}
	},
	JUMP(KeyEvent.VK_SPACE) {
		@Override
		public void action(Entity entity) {
			entity.moveJump();
		}

		@Override
		public void actionReverse(Entity entity) {
			entity.moveStop();
		}
	};
	
	private Integer input;
	public abstract void action(Entity entity);
	public abstract void actionReverse(Entity entity);
	
	EMove(Integer input) {
		this.input = input;
	}
	
	public Integer getInput() {
		return input;
	}
}