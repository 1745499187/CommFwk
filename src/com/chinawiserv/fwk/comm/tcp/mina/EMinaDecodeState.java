package com.chinawiserv.fwk.comm.tcp.mina;

public enum EMinaDecodeState {
	NEW,
	HEAD,
	MSG,
	MSG_IMCOMPLETE;
}
