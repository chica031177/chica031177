package tw.edu.ntut.csie.game.state;

import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.Audio;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.Animation;
import tw.edu.ntut.csie.game.extend.BitmapButton;
import tw.edu.ntut.csie.game.extend.Integer;

public class StateRun extends GameState {
    public static final int DEFAULT_SCORE_DIGITS = 4;
    private MovingBitmap _background;
    private MovingBitmap _android;
    private MovingBitmap _cloud;
    private MovingBitmap _door;
    private MovingBitmap _message;
    private MovingBitmap _keyleft;
    private MovingBitmap _keyright;
    private MovingBitmap _keyjump;

    private int _maptemp;
    private int _level;

    private Animation _flower;

    private Integer _scores;

    private boolean _grabl, _grabr, _grabj;
    private int _cx, _cy;

    private Audio _music;

    public StateRun(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.level1);
        _message = new MovingBitmap(R.drawable.message, 62, 205);

        _android = new MovingBitmap(R.drawable.android_green);
        _android.setLocation(40, 234);

        _keyleft = new MovingBitmap(R.drawable.left1);
        _keyleft.setLocation(20,306);

        _keyright = new MovingBitmap(R.drawable.right1);
        _keyright.setLocation(100,306);

        _keyjump = new MovingBitmap(R.drawable.jump1);
        _keyjump.setLocation(530, 306);

        _cloud = new MovingBitmap(R.drawable.cloud);
        _cx = 100;
        _cy = 50;
        _cloud.setLocation(_cx, _cy);

        _door = new MovingBitmap(R.drawable.water_tower);
        _door.setLocation(540, 226);
        _door.setVisible(false);

        _scores = new Integer(DEFAULT_SCORE_DIGITS, 50, 550, 10);

        _flower = new Animation();
        _flower.setLocation(560, 310);
        _flower.addFrame(R.drawable.flower1);
        _flower.addFrame(R.drawable.flower2);
        _flower.addFrame(R.drawable.flower3);
        _flower.addFrame(R.drawable.flower4);
        _flower.addFrame(R.drawable.flower5);
        _flower.setDelay(2);

        _music = new Audio(R.raw.peacherine_rag);
        _music.setRepeating(true);
        _music.play();

        _maptemp = 0;
        _level = 1;

        _grabl = false;
        _grabr = false;
        _grabj = false;
    }

    @Override
    public void move() {
        _flower.move();
        _cloud.setLocation(_cx, _cy);

            //跳起來
            if (_grabj && _android.getY() + _android.getHeight() + _maptemp == 296)
                if (_android.getY() > 0)
                    _android.setLocation(_android.getX(), _android.getY() - 75);

            //地心引力
            if (_android.getY() + _android.getHeight() + _maptemp < 296) {
                _android.setLocation(_android.getX(), _android.getY() + 1);
            }

            //走路
            if (_grabl)
                _android.setLocation(_android.getX() - 2, _android.getY());
            else if (_grabr)
                _android.setLocation(_android.getX() + 2, _android.getY());

        if(_level == 1) {
            //第一張圖地洞
            if (_android.getX() > 320 && _android.getX() < 400 && _android.getY() + _android.getHeight() >= 296) {
                _android.setLocation(_android.getX(), _android.getY() + 1);
            }

            //第一張圖樓梯
            //第一階
            if (_android.getX() + _android.getWidth() >= 520 && _android.getY() + _android.getHeight() > 256) {
                _android.setLocation(520 - _android.getWidth(), _android.getY());
            } else if (_android.getX() + _android.getWidth() >= 520) {
                _maptemp = 40;
                //第二階
                if (_android.getX() + _android.getWidth() >= 560 && _android.getY() + _android.getHeight() > 216) {
                    _android.setLocation(560 - _android.getWidth(), _android.getY());
                } else if (_android.getX() + _android.getWidth() >= 560) {
                    _maptemp = 80;
                    //第三階
                    if (_android.getX() + _android.getWidth() >= 600 && _android.getY() + _android.getHeight() > 176) {
                        _android.setLocation(600 - _android.getWidth(), _android.getY());
                    } else if (_android.getX() + _android.getWidth() >= 600) {
                        _maptemp = 120;
                    }
                }
            } else {
                _maptemp = 0;
            }
            if (_android.getX() == 640)
                _level = 2;
        } else if (_level == 2){
            _background = new MovingBitmap(R.drawable.level2);
            _door.setVisible(true);
            _maptemp = 0;
            _android.setLocation(0, _android.getY());
            //第二張圖地洞
            if (_android.getX() < 160 && _android.getY() + _android.getHeight() >= 296) {
                _android.setLocation(_android.getX(), _android.getY() + 1);
            }
        }
    }

    @Override
    public void show() {
        // �I�s���Ǭ��K�϶���
        _background.show();
        _flower.show();
        _message.show();
        _door.show();
        _android.show();
        _cloud.show();
        _scores.show();
        _keyleft.show();
        _keyright.show();
        _keyjump.show();
    }

    @Override
    public void release() {
        _background.release();
        _scores.release();
        _android.release();
        _flower.release();
        _cloud.release();
        _message.release();
        _music.release();
        _door.release();
        _keyleft.release();
        _keyright.release();
        _keyjump.release();

        _background = null;
        _scores = null;
        _android = null;
        _flower = null;
        _message = null;
        _cloud = null;
        _music = null;
        _door = null;
        _keyleft = null;
        _keyright = null;
        _keyjump = null;
    }

    @Override
    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {
        if (roll > 15 && roll < 60 && _cx > 50)
            _cx -= 2;
        if (roll < -15 && roll > -60 && _cx + _cloud.getWidth() < 500)
            _cx += 2;
    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        _message.setVisible(false);
        if (pointers.size() == 1) {
            int touchX = pointers.get(0).getX();
            int touchY = pointers.get(0).getY();

            if (touchX > _keyjump.getX() && touchX < _keyjump.getX() + _keyjump.getWidth() &&
                    touchY > _keyjump.getY() && touchY < _keyjump.getY() + _keyjump.getHeight()) {
                _keyjump = new MovingBitmap(R.drawable.jump2);
                _keyjump.setLocation(530, 306);
                _grabj = true;
            } else {
                _grabj = false;
            }

            if (touchX > _keyleft.getX() && touchX < _keyleft.getX() + _keyleft.getWidth() &&
                    touchY > _keyleft.getY() && touchY < _keyleft.getY() + _keyleft.getHeight()) {
                _keyleft = new MovingBitmap(R.drawable.left2);
                _keyleft.setLocation(20,306);
                _grabl = true;
                _grabr = false;
            } else if (touchX > _keyright.getX() && touchX < _keyright.getX() + _keyright.getWidth() &&
                    touchY > _keyright.getY() && touchY < _keyright.getY() + _keyright.getHeight()) {
                _keyright = new MovingBitmap(R.drawable.right2);
                _keyright.setLocation(100,306);
                _grabl = false;
                _grabr = true;
            } else {
                _grabl = false;
                _grabr = false;
            }
        }
        return true;
    }

    @Override
    public boolean pointerMoved(List<Pointer> pointers) {
        int moveX = _android.getX();
        int moveY = _android.getY();
        if (moveX + _android.getWidth() / 2 > _door.getX() && moveX < _door.getX() + _door.getWidth() / 2 &&
                moveY + _android.getHeight() / 2 > _door.getY() && moveY < _door.getY() + _door.getHeight() / 2) {
            changeState(Game.OVER_STATE);
        } else if (moveY == 376) {
            changeState(Game.RUNNING_STATE);
        }
        return false;
    }

    @Override
    public boolean pointerReleased(List<Pointer> pointers) {
        _keyjump = new MovingBitmap(R.drawable.jump1);
        _keyjump.setLocation(530, 306);
        _keyleft = new MovingBitmap(R.drawable.left1);
        _keyleft.setLocation(20,306);
        _keyright = new MovingBitmap(R.drawable.right1);
        _keyright.setLocation(100,306);

        _grabj = false;
        _grabl = false;
        _grabr = false;
        return false;
    }

    @Override
    public void pause() {
        _music.pause();
    }

    @Override
    public void resume() {
        _music.resume();
    }
}
