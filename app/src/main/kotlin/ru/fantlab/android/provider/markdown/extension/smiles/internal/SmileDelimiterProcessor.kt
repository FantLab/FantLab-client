package ru.fantlab.android.provider.markdown.extension.smiles.internal

import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

import ru.fantlab.android.provider.markdown.extension.smiles.Smile

class SmileDelimiterProcessor : DelimiterProcessor {

	override fun getOpeningCharacter(): Char {
		return ':'
	}

	override fun getClosingCharacter(): Char {
		return ':'
	}

	override fun getMinLength(): Int {
		return 1
	}

	override fun getDelimiterUse(opener: DelimiterRun, closer: DelimiterRun): Int {
		return if (opener.length() >= 1 && closer.length() >= 1) {
			1
		} else {
			0
		}
	}

	override fun process(opener: Text, closer: Text, delimiterCount: Int) {
		val smile = Smile()
		var tmp: Node? = opener.next
		while (tmp != null && tmp !== closer) {
			val next = tmp.next
			smile.appendChild(tmp)
			tmp = next
		}
		opener.insertAfter(smile)
	}
}